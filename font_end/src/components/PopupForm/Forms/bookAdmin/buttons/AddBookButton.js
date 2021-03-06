import React from "react";
import Button from "@mui/material/Button";

const AddBookButton = ({ buttonRef, showModal }) => {
  return (
    <Button variant="contained" ref={buttonRef} onClick={showModal}>
      +ADD BOOK
    </Button>
  );
};
export default AddBookButton;
