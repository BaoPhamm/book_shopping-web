import axios from "axios";
import AuthHeader from "../AuthHeader";

const REGISTRATION_API_BASE_URL = "http://localhost:8080/api/v1/registration";

class RegistrationService {
  async registration(registrationRequest) {
    const response = await axios
      .post(REGISTRATION_API_BASE_URL, registrationRequest, {
        headers: AuthHeader(),
      })
      .then(function (response) {
        console.log(response);
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }
}

export default new RegistrationService();
