import React, { useState, useEffect } from "react";
import { ArrowLeftOutlined, ArrowRightOutlined } from "@material-ui/icons";
import styled from "styled-components";
import BookService from "../services/user/BookService";
import { mobile } from "../responsive";
import { useNavigate } from "react-router-dom";

const Container = styled.div`
  width: 100%;
  height: 90vh;
  margin-top: 2rem;
  display: flex;
  position: relative;
  overflow: hidden;
  ${mobile({ display: "none" })}
`;

const Arrow = styled.div`
  width: 50px;
  height: 50px;
  background-color: #fff7f7;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 0;
  bottom: 0;
  left: ${(props) => props.direction === "left" && "10px"};
  right: ${(props) => props.direction === "right" && "10px"};
  margin: auto;
  cursor: pointer;
  opacity: 0.5;
  z-index: 2;
`;

const Wrapper = styled.div`
  height: 100%;
  display: flex;
  transition: all 1.5s ease;
  transform: translateX(${(props) => props.slideIndex * -100}vw);
`;

const Slide = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  background-color: #${(props) => props.bg};
`;

const ImgContainer = styled.div`
  height: 100%;
  flex: 1;
`;

const Image = styled.img`
  height: 80%;
`;

const InfoContainer = styled.div`
  flex: 1;
  padding: 50px;
`;

const Title = styled.h1`
  font-size: 70px;
`;

const Desc = styled.p`
  margin: 50px 0px;
  font-size: 20px;
  font-weight: 500;
  letter-spacing: 3px;
`;

const Button = styled.button`
  padding: 10px;
  font-size: 20px;
  background-color: transparent;
  cursor: pointer;
`;

const Slider = () => {
  const [slideIndex, setSlideIndex] = useState(0);
  const [featuresProducts, setFeaturesProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const backgroundColor = ["f5fafd", "fcf1ed", "fbf0f4"];
  let navigate = useNavigate();

  const handleClick = (direction) => {
    if (direction === "left") {
      setSlideIndex(slideIndex > 0 ? slideIndex - 1 : 2);
    } else {
      setSlideIndex(slideIndex < 2 ? slideIndex + 1 : 0);
    }
  };

  useEffect(() => {
    GetFeaturesBooks();
  }, []);

  const GetFeaturesBooks = async () => {
    await setIsLoading(true);
    BookService.getFeaturesBooks().then(async (res) => {
      await setFeaturesProducts([...res]);
      await setIsLoading(false);
    });
  };

  const OnclickHandle = (id) => {
    navigate("/products/" + id);
  };

  return !isLoading ? (
    <Container>
      <Arrow direction="left" onClick={() => handleClick("left")}>
        <ArrowLeftOutlined />
      </Arrow>
      <Wrapper slideIndex={slideIndex}>
        {featuresProducts.map((item) => (
          <Slide bg={backgroundColor[slideIndex]} key={item.id}>
            <ImgContainer>
              <Image src={item.imgSrc} />
            </ImgContainer>
            <InfoContainer>
              <Title>{item.title}</Title>
              <Desc>{item.description}</Desc>
              <Button onClick={() => OnclickHandle(item.id)}>SHOW NOW</Button>
            </InfoContainer>
          </Slide>
        ))}
      </Wrapper>
      <Arrow direction="right" onClick={() => handleClick("right")}>
        <ArrowRightOutlined />
      </Arrow>
    </Container>
  ) : (
    ""
  );
};

export default Slider;
