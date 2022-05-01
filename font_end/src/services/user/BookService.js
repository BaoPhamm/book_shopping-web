import axios from "axios";

const BOOK_API_BASE_URL = "http://localhost:8080/api/v1/books";

class BookService {
  getAllBooks() {
    return axios.get(BOOK_API_BASE_URL);
  }

  getBookById(Id) {
    return axios.get(BOOK_API_BASE_URL + "/" + Id);
  }

  //   createEmployee(employee) {
  //     return axios.post(EMPLOYEE_API_BASE_URL, employee);
  //   }

  //   getEmployeeById(employeeId) {
  //     return axios.get(EMPLOYEE_API_BASE_URL + "/" + employeeId);
  //   }

  //   updateEmployee(employee, employeeId) {
  //     return axios.put(EMPLOYEE_API_BASE_URL + "/" + employeeId, employee);
  //   }

  //   deleteEmployee(employeeId) {
  //     return axios.delete(EMPLOYEE_API_BASE_URL + "/" + employeeId);
  //   }
}

export default new BookService();
