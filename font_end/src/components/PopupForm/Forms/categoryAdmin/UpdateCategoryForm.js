import React, { useState } from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 95%;
  background-color: white;
  text-align: center;
`;
const RowWrapper = styled.div`
  justify-content: start;
  width: 100%;
  display: grid;
  grid-template-columns: 13% 87%;
  align-items: center;
  margin-top: 5px;
`;
const Text = styled.p`
  font-size: 15px;
  text-align: left;
`;
const Input = styled.input`
  min-width: 80%;
  padding: 7px;
  right: 0;
`;
const InputDescription = styled.textarea`
  font-size: 14px;
  font-family: "Arial";
  min-width: 80%;
  height: 3.2rem;
  overflow-wrap: break-word;
  padding: 7px;
  right: 0;
`;
const Title = styled.h1`
  font-size: 24px;
  font-weight: 300;
`;
const Button = styled.button`
  width: 30%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const UpdateCategoryForm = ({ onSubmit, categoryDetails }) => {
  const [categoryImageURL, setcategoryImageURL] = useState(
    categoryDetails.imgSrc
  );

  const onChangeCatImg = async (event) => {
    await setcategoryImageURL(URL.createObjectURL(event.target.files[0]));
  };

  return (
    <Wrapper>
      <Title>UPDATE CATEGORY</Title>
      <form onSubmit={onSubmit}>
        <RowWrapper>
          <Text>ID:</Text>
          <Input
            disabled={true}
            type="text"
            defaultValue={categoryDetails.id}
            name="categoryid"
            placeholder="Category ID"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Name:</Text>
          <Input
            type="text"
            defaultValue={categoryDetails.name}
            name="categoryname"
            placeholder="Category name"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Description:</Text>
          <InputDescription
            type="text"
            defaultValue={categoryDetails.description}
            name="description"
            placeholder="Category description"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Image URL:</Text>
          <Input
            disabled={true}
            value={categoryDetails.imgSrc}
            type="text"
            name="imgURL"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Image preview:</Text>
          <Input type="file" name="imgFile" onChange={onChangeCatImg} />
          <img
            src={categoryImageURL}
            width="200"
            alt="https://firebasestorage.googleapis.com/v0/b/shopping-web-a0eb0.appspot.com/o/category_img%2Fdefault.jpg"
          />
        </RowWrapper>
        <Button>UPDATE</Button>
      </form>
    </Wrapper>
  );
};
export default UpdateCategoryForm;
