import axios from "axios";

const ORDER_API_BASE_URL = "http://localhost:8080/api/v1/user/orders";

class OrderService {
  async getUserOrders(token) {
    const response = await axios
      .get(ORDER_API_BASE_URL, {
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

  async postOrder(token, orderRequest) {
    const response = await axios
      .post(ORDER_API_BASE_URL, orderRequest, {
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
}

export default new OrderService();
