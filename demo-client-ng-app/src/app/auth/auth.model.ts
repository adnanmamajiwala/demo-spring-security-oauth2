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
  sub: string;
  authorities: Set<string>;
  scope: Set<string>;
}