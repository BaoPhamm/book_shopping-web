import axios from "axios";

const USER_API_BASE_URL = "http://localhost:8080/api/v1";

class UserService {
  getAllUsers(token) {
    return axios.get(USER_API_BASE_URL + "/admin/users", {
      headers: {
        Authorization: token,
        // Overwrite Axios's automatically set Content-Type
      },
    });
  }

  login(authenticationRequest) {
    console.log(authenticationRequest);
    return axios.post(
      USER_API_BASE_URL + "/auth/login",
      authenticationRequest,
      {
        headers: {
          // Overwrite Axios's automatically set Content-Type
          "Content-Type": "application/json",
        },
      }
    );
  }
}

export default new UserService();
