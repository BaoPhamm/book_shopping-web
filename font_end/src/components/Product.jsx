import styled from "styled-components";
import React from "react";
import { Link } from "react-router-dom";
import Rating from "@material-ui/lab/Rating";

const Container = styled.div`
  flex: 1;
  margin: 5px;
  width: 18vw;
  height: 400px;
  display: grid-column;
  align-items: center;
  background-color: #f5fbfd;
  position: relative;
  text-align: center;
  border-radius: 20px;
  border: 1px solid #def2ff;
`;

const WrapperImg = styled.div`
  width: 100%;
  height: 65%;
  align-items: center;
  position: relative;
`;

const WrapperTitle = styled.div`
  width: 100%;
  height: 15%;
  align-items: center;
  position: relative;
`;

const WrapperPrice = styled.div`
  width: 100%;
  height: 7%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  bottom: 5px;
`;

const WrapperRating = styled.div`
  width: 100%;
  height: 8%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  bottom: 5px;
`;

const Image = styled.img`
  margin-top: 12px;
  height: 95%;
`;

const Title = styled.p`
  font-size: 19px;
`;
const Price = styled.p`
  font-size: 17px;
`;
const WrapperTotalRatingsText = styled.p`
  margin-left: 0.5rem;
  font-size: 19px;
`;

const Product = ({ productItem }) => {
  const formatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "VND",
    minimumFractionDigits: 0,
  });

  return (
    <Container>
      <Link
        to={"/products/" + productItem.id}
        style={{ textDecoration: "none", color: "black" }}
      >
        <WrapperImg>
          <Image src={productItem.imgSrc} />
        </WrapperImg>
        <WrapperTitle>
          <Title>{productItem.title} </Title>
        </WrapperTitle>
      </Link>
      <WrapperPrice>
        <Price>{formatter.format(productItem.price) + " VND"} </Price>
      </WrapperPrice>
      <WrapperRating>
        <Rating
          name="Rating Label"
          value={productItem.ratingPoint}
          disabled="true"
          precision={0.5}
        />
        <WrapperTotalRatingsText>
          <em>{productItem.totalRatings}</em>
        </WrapperTotalRatingsText>
      </WrapperRating>
    </Container>
  );
};

export default Product;
