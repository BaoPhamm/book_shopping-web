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

const Products = () => {
  const [allProducts, setAllProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    console.log("useEffect");
    GetAllBooks();
  }, []);

  const GetAllBooks = async () => {
    await setIsLoading(true);
    BookService.getAllBooks().then(async (res) => {
      console.log(res);
      await setAllProducts([...res]);
      console.log("AAAAA");
      await setIsLoading(false);
    });
  };

  return (
    <Container>
      {console.log("DOM RENDER")}
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
