import {Directive, Input, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';
import {User} from './auth.model';

@Directive({
  selector: '[appHasRole]'
})
export class HasRoleDirective implements OnInit {

  requiredRoles: string[];

  constructor(private templateRef: TemplateRef<any>,
              private jwtHelperService: JwtHelperService,
              private viewContainer: ViewContainerRef) {
  }

  @Input()
  set appHasRole(roles: string[]) {
    if (!roles || !roles.length) {
      throw new Error('Roles value is empty or missed');
    }
    this.requiredRoles = roles;
  }

  ngOnInit(): void {
    if (this.hasRoles()) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
      this.viewContainer.clear();
    }
  }

  private hasRoles(): boolean {
    let hasAccess: boolean = false;
    const user = new User(this.jwtHelperService.decodeToken());
    if (!!user) {
      hasAccess = true;
      for (const role of this.requiredRoles) {
        if (!user.authorities.has(role)) return false;
      }
    }
    return hasAccess;
  }

}
