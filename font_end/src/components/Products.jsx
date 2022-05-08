import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { mobile } from "../responsive";
import Product from "./Product";
import BookService from "../services/user/BookService";

const Container = styled.div`
  padding: 10px;
  display: grid;
  grid-template-columns: 23vw 23vw 23vw 23vw;
  justify-content: start;
  ${mobile({ display: "none" })}
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
              <Product productItem={item} key={item.id} />
            </div>
          ))
        : ""}
    </Container>
  );
};

export default Products;
