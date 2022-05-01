import axios from "axios";

const AUTH_API_BASE_URL = "http://localhost:8080/api/v1";

class AuthService {
  async getUserInfo(token) {
    const response = await axios
      .get(AUTH_API_BASE_URL + "/user/info", {
        headers: {
          Authorization: "Bearer " + token,
        },
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  async login(authenticationRequest) {
    console.log(authenticationRequest);
    const response = await axios
      .post(AUTH_API_BASE_URL + "/auth/login", authenticationRequest, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then(function (response) {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }

  logout() {
    localStorage.removeItem("user");
  }
}

export default new AuthService();
