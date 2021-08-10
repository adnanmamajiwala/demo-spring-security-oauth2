export class AuthJwt {
  access_token: string;
  token_type: string;
  refresh_token: string;
  expires_in: number;
  scope: string;
  jti: string;
}

export class User {
  firstName: string;
  lastName: string;
  username: string;
  authorities: Set<string>;
  scope: Set<string>;

  constructor(object: any = null) {
    this.firstName = object?.firstName;
    this.lastName = object?.lastName;
    this.username = object?.sub;
    this.authorities = new Set<string>(object?.authorities);
    this.scope = new Set<string>(object?.scope);
  }
}
