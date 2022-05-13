import React from "react";
import styled from "styled-components";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";

const Wrapper = styled.div`
  width: 95%;
  background-color: white;
  text-align: center;
`;
const RowWrapper = styled.div`
  justify-content: start;
  width: 100%;
  display: grid;
  grid-template-columns: 10% 90%;
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
const Title = styled.h1`
  font-size: 24px;
  font-weight: 300;
`;
const CategoriesText = styled.h2`
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

const AddCategoryToBookForm = ({ onSubmit, productDetails, allCategories }) => {
  const getCategogiesString = () => {
    let CategogiesString = "";
    productDetails.categories.map(
      (category) => (CategogiesString += category.name + " - ")
    );
    return CategogiesString.slice(0, -2);
  };
  return (
    <Wrapper>
      <Title>ADD CATEGORIES TO BOOK</Title>
      <form onSubmit={onSubmit}>
        <RowWrapper>
          <Text>ID:</Text>
          <Input
            disabled={true}
            type="number"
            defaultValue={productDetails.id}
            name="id"
            placeholder="ID"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Title:</Text>
          <Input
            disabled={true}
            type="text"
            defaultValue={productDetails.title}
            name="title"
            placeholder="ID"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Categories:</Text>
          <Input
            disabled={true}
            type="text"
            defaultValue={getCategogiesString()}
            name="categories"
            placeholder="Categories"
          />
        </RowWrapper>
        <CategoriesText>Choose categories to add</CategoriesText>
        <FormGroup>
          {allCategories.map((category) => (
            <FormControlLabel
              control={<Checkbox name={"Id" + category.id} />}
              label={category.name}
            />
          ))}
        </FormGroup>
        <Button>ADD</Button>
      </form>
    </Wrapper>
  );
};
export default AddCategoryToBookForm;
