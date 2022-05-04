import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Product from "./Product";
import { Link } from "react-router-dom";
import BookService from "../services/user/BookService";

const Container = styled.div`
  padding: 20px;
  display: flex;
  flex-wrap: wrap;
  justify-content: start;
`;

const Products = (props) => {
  const [allProducts, setAllProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    GetAllBooks();
  }, [props.selectedCategory]);

  const GetAllBooks = async () => {
    await setIsLoading(true);
    if (props.selectedCategory == 0) {
      BookService.getAllBooks().then(async (res) => {
        await setAllProducts([...res]);
        await setIsLoading(false);
      });
    } else {
      BookService.getBooksByCategory(props.selectedCategory).then(
        async (res) => {
          await setAllProducts([...res]);
          await setIsLoading(false);
        }
      );
    }
  };

  return (
    <Container>
      {!isLoading
        ? allProducts.map((item) => (
            <div>
              <Link to={"/products/" + item.id}>
                <Product productItem={item} key={item.id} />
              </Link>
            </div>
          ))
        : ""}
    </Container>
  );
};

export default Products;
