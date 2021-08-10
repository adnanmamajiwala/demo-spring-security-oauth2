import {Component, OnInit} from '@angular/core';
import {AuthService} from './auth.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {mergeMap} from 'rxjs/operators';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService,
              private toastr: ToastrService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams
      .pipe(mergeMap((value: Params) => this.authService.retrieveToken(value.code)))
      .subscribe(() => this.router.navigate(['/']),
        error => {
          console.log(error);
          this.toastr.error('Uh-oh something went wrong while authenticating. Please try again', 'Error');
          this.router.navigate(['/']);
        });

  }
}
