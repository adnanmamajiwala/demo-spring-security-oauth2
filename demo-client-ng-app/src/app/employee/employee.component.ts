import {Component, OnInit} from '@angular/core';
import {EmployeeService} from './employee.service';
import {Employee} from './employee.model';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.scss']
})
export class EmployeeComponent implements OnInit {

  employees : Employee[] = [];

  constructor(private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.employeeService.getAll().subscribe((val) => this.employees = val);
  }

}
