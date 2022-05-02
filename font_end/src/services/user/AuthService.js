import axios from "axios";
import AuthHeader from "../AuthHeader";

const AUTH_API_BASE_URL = "http://localhost:8080/api/v1";

class AuthService {
  async login(authenticationRequest) {
    console.log(authenticationRequest);
    const response = await axios
      .post(AUTH_API_BASE_URL + "/auth/login", authenticationRequest, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        if (response.data.token) {
          localStorage.setItem("userLoginInfo", JSON.stringify(response.data));
        }
        return response;
      })
      .catch(function (error) {
        console.log(error);
      });
    return response;
  }
}

export default new AuthService();
