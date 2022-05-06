import styled from "styled-components";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import Rating from "@material-ui/lab/Rating";
import Typography from "@material-ui/core/Typography";
import Box from "@material-ui/core/Box";

const Container = styled.div`
  flex: 1;
  margin: 5px;
  min-width: 300px;
  height: 400px;
  display: grid-column;
  align-items: center;
  background-color: #f5fbfd;
  position: relative;
  text-align: center;
  border-radius: 20px;
  border: 1px solid #def2ff;
`;

const Wrapper = styled.div`
  width: 100%;
  height: 90%;
  display: grid-column;
  align-items: center;
  position: relative;
`;

const Image = styled.img`
  margin-top: 20px;
  height: 80%;
`;

const Title = styled.p`
  margin-top: 8px;
  font-size: 20px;
`;

const Product = ({ productItem }) => {
  return (
    <Container>
      <Wrapper>
        <Link
          to={"/products/" + productItem.id}
          style={{ textDecoration: "none", color: "black" }}
        >
          <Image src={productItem.imgSrc} />
          <Title>{productItem.title} </Title>
        </Link>
      </Wrapper>
      <Rating name="Rating Label" value={4} disabled="true" />
    </Container>
  );
};

export default Product;
