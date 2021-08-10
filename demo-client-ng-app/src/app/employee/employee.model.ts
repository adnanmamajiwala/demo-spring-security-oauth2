export class Employee {
  id: number;
  firstName: string;
  lastName: string;
  address: Address;
}

export class Address {
  id: number;
  street: string;
  state: string;
  zipCode: string;
  country: string;
}
