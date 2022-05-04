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
  const [selectedCategory, setSelectedCategory] = useState(0);

  useEffect(() => {
    GetAllCategories();
  }, []);

  const GetAllCategories = async () => {
    await setIsLoading(true);
    CategoryService.getAllCategories().then(async (res) => {
      await setAllCategories([...res]);
      await setIsLoading(false);
    });
  };

  const handleSelectChange = async (e) => {
    await setSelectedCategory(e.target.value);
  };

  return !isLoading ? (
    <Container>
      <Title>Books</Title>
      <FilterContainer>
        <Filter>
          <FilterText>Filter category:</FilterText>
          <Select defaultValue={0} onChange={handleSelectChange}>
            <Option value={0}>All</Option>
            {allCategories.map((item) => (
              <Option value={item.id}>{item.name}</Option>
            ))}
          </Select>
        </Filter>
        <Filter>
          <FilterText>Sort Products:</FilterText>
          <Select defaultValue="Newest">
            <Option>Newest</Option>
            <Option>Price (asc)</Option>
            <Option>Price (desc)</Option>
          </Select>
        </Filter>
      </FilterContainer>
      <Products selectedCategory={selectedCategory} />
    </Container>
  ) : (
    ""
  );
};

export default ProductList;
