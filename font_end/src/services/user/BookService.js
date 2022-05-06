import axios from "axios";

const BOOK_API_BASE_URL = "http://localhost:8080/api/v1/books";

class BookService {
  async getBookById(bookId) {
    const response = await axios
      .get(BOOK_API_BASE_URL + "/" + bookId)
      .then(function (response) {
        return response;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getAllBooks() {
    const response = await axios
      .get(BOOK_API_BASE_URL)
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getFeaturesBooks() {
    const response = await axios
      .get(BOOK_API_BASE_URL + "/features")
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }

  async getBooksByCategory(categoryId) {
    const response = await axios
      .get(BOOK_API_BASE_URL + "/category/" + categoryId)
      .then(function (response) {
        return response.data;
      })
      .catch(function (error) {
        console.log(error);
        return error.response;
      });
    return response;
  }
}

export default new BookService();
