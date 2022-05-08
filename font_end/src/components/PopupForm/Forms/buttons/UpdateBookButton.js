import React from "react";
import Button from "@mui/material/Button";

const UpdateBookButton = ({ buttonRef, showModal }) => {
  return (
    <Button variant="contained" ref={buttonRef} onClick={showModal}>
      UPDATE
    </Button>
  );
};
export default UpdateBookButton;
