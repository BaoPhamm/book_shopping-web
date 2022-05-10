import React from "react";
import ReactDOM from "react-dom";
import ChangePasswordForm from "./Forms/ChangePasswordForm";
import AddBookForm from "./Forms/bookAdmin/AddBookForm";
import UpdateBookForm from "./Forms/bookAdmin/UpdateBookForm";
import AddCategoryToBookForm from "./Forms/bookAdmin/AddCategoryToBookForm";
import RemoveCategoryFromBookForm from "./Forms/bookAdmin/RemoveCategoryFromBookForm";
import DeleteBookForm from "./Forms/bookAdmin/DeleteBookForm";
import FocusTrap from "focus-trap-react";

export const Modal = ({
  onClickOutside,
  onKeyDown,
  modalRef,
  buttonRef,
  closeModal,
  onSubmit,
  typeSubmit,
  productDetails,
  allCategories,
}) => {
  const submitForm = () => {
    if (typeSubmit === "changePassword") {
      return <ChangePasswordForm onSubmit={onSubmit} />;
    } else if (typeSubmit === "addBook") {
      return <AddBookForm onSubmit={onSubmit} />;
    } else if (typeSubmit === "updateBook") {
      return (
        <UpdateBookForm onSubmit={onSubmit} productDetails={productDetails} />
      );
    } else if (typeSubmit === "addCatToBook") {
      return (
        <AddCategoryToBookForm
          onSubmit={onSubmit}
          productDetails={productDetails}
          allCategories={allCategories}
        />
      );
    } else if (typeSubmit === "removeCatFromBook") {
      return (
        <RemoveCategoryFromBookForm
          onSubmit={onSubmit}
          productDetails={productDetails}
          allCategories={allCategories}
        />
      );
    } else if (typeSubmit === "deleteBook") {
      return (
        <DeleteBookForm
          onSubmit={onSubmit}
          onNoSubmit={closeModal}
          productDetails={productDetails}
        />
      );
    }
  };
  return ReactDOM.createPortal(
    <FocusTrap>
      <aside
        tag="aside"
        role="dialog"
        tabIndex="-1"
        aria-modal="true"
        className="modal-cover"
        onClick={onClickOutside}
        onKeyDown={onKeyDown}
      >
        <div className="modal-area" ref={modalRef}>
          <button
            ref={buttonRef}
            aria-label="Close Modal"
            aria-labelledby="close-modal"
            className="_modal-close"
            onClick={closeModal}
          >
            <span id="close-modal" className="_hide-visual">
              Close
            </span>
            <svg className="_modal-close-icon" viewBox="0 0 40 40">
              <path d="M 10,10 L 30,30 M 30,10 L 10,30" />
            </svg>
          </button>
          <div className="modal-body">{submitForm()}</div>
        </div>
      </aside>
    </FocusTrap>,
    document.body
  );
};

export default Modal;
