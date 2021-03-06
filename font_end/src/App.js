import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Error from "./pages/Error";
import UpdateProfile from "./pages/UpdateProfile";
import ProductList from "./pages/ProductList";
import Product from "./pages/Product";
import Admin from "./pages/Admin";
import ManageAdmin from "./pages/ManageAdmin";
import { Provider } from "react-redux";
import store from "./store";

const App = () => {
  return (
    <Provider store={store}>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/manage-admin" element={<ManageAdmin />} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/profile" element={<UpdateProfile />} />
          <Route path="/products" element={<ProductList />} />
          <Route path="/products/:id" element={<Product />} />
          <Route path="/products/not-found" element={<Error />} />
          <Route path="/:someString" element={<Error />} />
          <Route path="/products/:someString" element={<Error />} />
        </Routes>
        <Footer />
      </Router>
    </Provider>
  );
};

export default App;
