import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth/auth.service';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from '../auth/auth.model';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  isAuthenticated$: Observable<boolean>;
  user: User;
  constructor(private authService: AuthService,
              private jwtHelper: JwtHelperService) {
  }

  ngOnInit(): void {
    this.isAuthenticated$ = this.authService.isAuthenticated$;
    this.user = new User(this.jwtHelper.decodeToken<User>());
  }

  login() {
    this.authService.login();
  }
}
