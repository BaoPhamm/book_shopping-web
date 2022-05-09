import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Products from "../components/Products";
import { mobile } from "../responsive";
import CategoryService from "../services/user/CategoryService";

const Container = styled.div``;

const Title = styled.h1`
  margin: 20px;
`;
const FilterContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;
const Filter = styled.div`
  margin: 20px;
  ${mobile({ width: "0px 20px", display: "flex", flexDirection: "column" })}
`;
const FilterText = styled.span`
  font-size: 20px;
  font-weight: 600;
  margin-right: 20px;
  ${mobile({ marginRight: "0px" })}
`;
const Select = styled.select`
  padding: 10px;
  margin-right: 20px;
  ${mobile({ margin: "10px 0px" })}
`;
const Option = styled.option``;

const ProductList = () => {
  const [allCategories, setAllCategories] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [dataChange, toggleDataChange] = useState(false);

  useEffect(() => {
    GetAllCategories();
  }, [dataChange]);

  const GetAllCategories = async () => {
    await setIsLoading(true);
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
      await setIsLoading(false);
    });
  };

  const handleSelectChange = async (e) => {
    localStorage.setItem("FilterSelectedCategory", e.target.value);
    toggleDataChange(!dataChange);
  };

  return !isLoading ? (
    <Container>
      <Title>Books</Title>
      <FilterContainer>
        <Filter>
          <FilterText>Filter category:</FilterText>
          <Select
            defaultValue={localStorage.getItem("FilterSelectedCategory")}
            onChange={handleSelectChange}
          >
            <Option value={0}>All</Option>
            {allCategories.map((item) => (
              <Option value={item.id}>{item.name}</Option>
            ))}
          </Select>
        </Filter>
      </FilterContainer>
      <Products
        selectedCategory={localStorage.getItem("FilterSelectedCategory")}
      />
    </Container>
  ) : (
    ""
  );
};

export default ProductList;
