import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from './auth.model';

@Directive({
  selector: '[appUserRole]',
})
export class UserRoleDirective implements OnInit {

  userRoles: string[];

  constructor(private templateRef: TemplateRef<any>,
              private jwtHelperService: JwtHelperService,
              private viewContainer: ViewContainerRef) {
  }

  @Input()
  set appUserRole(roles: string[]) {
    if (!roles || !roles.length) {
      throw new Error('Roles value is empty or missed');
    }
    this.userRoles = roles;
  }

  ngOnInit(): void {
    let hasAccess = false;
    const user = this.jwtHelperService.decodeToken<User>();
    if (!!user) {
      hasAccess = this.userRoles.some(r => user.authorities.has(r));
    }

    if (hasAccess) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

}
