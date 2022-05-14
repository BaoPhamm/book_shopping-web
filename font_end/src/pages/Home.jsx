import React from "react";
import Slider from "../components/Slider";
import Categories from "../components/Categories";
import Products from "../components/Products";
import styled from "styled-components";

const Title = styled.h1`
  margin: 20px;
`;
const ProductsContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const Home = () => {
  return (
    <div>
      <Slider />
      <Categories />
      <ProductsContainer>
        <Title>All Books</Title>
        <Products selectedCategory={0} />
      </ProductsContainer>
    </div>
  );
};

export default Home;
