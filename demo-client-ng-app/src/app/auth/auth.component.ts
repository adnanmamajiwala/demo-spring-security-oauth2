import {Component, OnInit} from '@angular/core';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss'],
})
export class AuthComponent implements OnInit {

  constructor(private authService: AuthService,
              private toastr: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
    let indexOf = window.location.href.indexOf('code');
    if (indexOf != -1) {
      const code = window.location.href.substring(indexOf + 5);
      this.authService.retrieveToken(code)
        .catch((err) => {
          console.log(err)
          this.toastr.error('Uh-oh something went wrong while authenticating. Please try again', 'Error');
        })
        .finally(() => this.router.navigate(['/']));
    }
  }
}
