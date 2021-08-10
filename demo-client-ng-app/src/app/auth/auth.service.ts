import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AuthJwt} from './auth.model';
import {Router} from '@angular/router';
import {BehaviorSubject, interval} from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private readonly clientId = 'ng-client';
  private readonly clientSecret = 'secret';
  private readonly redirectUri = 'http://localhost:4200/authorized';
  private readonly baseAuthUri = 'http://localhost:9000/oauth';
  private helper: JwtHelperService = new JwtHelperService();
  private authJwt: AuthJwt = null;
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

  constructor(private http: HttpClient,
              private router: Router) {
    const data = localStorage.getItem('jwt-token');
    if (!!data) {
      this.authJwt = JSON.parse(data);
    }
    interval(15000)
      .subscribe(() => {
        const val = this.isAuthenticated();
        if (!val) {
          localStorage.removeItem('jwt-token');
        }
        this.isAuthenticatedSubject.next(val);
      });
  }

  private isAuthenticated(): boolean {
    return !!this.authJwt && !this.helper.isTokenExpired(this.authJwt.access_token);
  }

  retrieveToken(code: string): Promise<AuthJwt> {
    let params = new URLSearchParams();
    params.append('grant_type', 'authorization_code');
    params.append('redirect_uri', this.redirectUri);
    params.append('code', code);

    let headers = new HttpHeaders({
      'Content-type': 'application/x-www-form-urlencoded; charset=utf-8',
      'Authorization': `Basic ${btoa(`${this.clientId}:${this.clientSecret}`)}`,
    });

    return this.http.post<AuthJwt>(`${this.baseAuthUri}/token`, params.toString(), {headers: headers})
      .pipe(
        map(jwt => {
          localStorage.setItem('jwt-token', JSON.stringify(jwt));
          this.authJwt = jwt;
          this.isAuthenticatedSubject.next(true);
          return jwt;
        }),
      ).toPromise();
  }

  login() {
    window.location.href = `${this.baseAuthUri}/authorize?client_id=${this.clientId}&redirect_uri=${this.redirectUri}&scope=openid&response_type=code`;
  }

  logout() {
    localStorage.removeItem('jwt-token');
    this.authJwt = null;
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/']).then();
  }
}


