import React from "react";
import ReactDOM from "react-dom";
import ChangePasswordForm from "./Forms/ChangePasswordForm";
import AddBookForm from "./Forms/bookAdmin/AddBookForm";
import UpdateBookForm from "./Forms/bookAdmin/UpdateBookForm";
import AddCategoryToBookForm from "./Forms/bookAdmin/AddCategoryToBookForm";
import RemoveCategoryFromBookForm from "./Forms/bookAdmin/RemoveCategoryFromBookForm";
import DeleteBookForm from "./Forms/bookAdmin/DeleteBookForm";
import AddRoleToUserForm from "./Forms/userAdmin/AddRoleToUserForm";
import RemoveRoleFromUserForm from "./Forms/userAdmin/RemoveRoleFromUserForm";
import DeleteUserForm from "./Forms/userAdmin/DeleteUserForm";
import BlockUserForm from "./Forms/userAdmin/BlockUserForm";
import AddNewRoleForm from "./Forms/roleAdmin/AddNewRoleForm";
import UpdateRoleForm from "./Forms/roleAdmin/UpdateRoleForm";
import DeleteRoleForm from "./Forms/roleAdmin/DeleteRoleForm";
import AddNewCategoryForm from "./Forms/categoryAdmin/AddNewCategoryForm";
import UpdateCategoryForm from "./Forms/categoryAdmin/UpdateCategoryForm";
import DeleteCategoryForm from "./Forms/categoryAdmin/DeleteCategoryForm";
import FocusTrap from "focus-trap-react";

export const Modal = ({
  onClickOutside,
  onKeyDown,
  modalRef,
  buttonRef,
  closeModal,
  onSubmit,
  typeSubmitGroup,
  typeSubmit,
  productDetails,
  allCategories,
  userDetails,
  allRoles,
  roleDetails,
  categoryDetails,
}) => {
  const submitForm = () => {
    if (typeSubmit === "changePassword") {
      return <ChangePasswordForm onSubmit={onSubmit} />;
    }
    // BOOK
    else if (typeSubmitGroup === "book") {
      if (typeSubmit === "addNew") {
        return <AddBookForm onSubmit={onSubmit} />;
      } else if (typeSubmit === "update") {
        return (
          <UpdateBookForm onSubmit={onSubmit} productDetails={productDetails} />
        );
      } else if (typeSubmit === "addCat") {
        return (
          <AddCategoryToBookForm
            onSubmit={onSubmit}
            productDetails={productDetails}
            allCategories={allCategories}
          />
        );
      } else if (typeSubmit === "removeCat") {
        return (
          <RemoveCategoryFromBookForm
            onSubmit={onSubmit}
            productDetails={productDetails}
            allCategories={allCategories}
          />
        );
      } else if (typeSubmit === "delete") {
        return (
          <DeleteBookForm
            onSubmit={onSubmit}
            onNoSubmit={closeModal}
            productDetails={productDetails}
          />
        );
      }
    }
    // USER
    else if (typeSubmitGroup === "user") {
      if (typeSubmit === "addRole") {
        return (
          <AddRoleToUserForm
            onSubmit={onSubmit}
            userDetails={userDetails}
            allRoles={allRoles}
          />
        );
      } else if (typeSubmit === "removeRole") {
        return (
          <RemoveRoleFromUserForm
            onSubmit={onSubmit}
            userDetails={userDetails}
          />
        );
      } else if (typeSubmit === "delete") {
        return (
          <DeleteUserForm
            onSubmit={onSubmit}
            userDetails={userDetails}
            onNoSubmit={closeModal}
          />
        );
      } else if (typeSubmit === "block") {
        return (
          <BlockUserForm
            onSubmit={onSubmit}
            userDetails={userDetails}
            onNoSubmit={closeModal}
          />
        );
      }
    }
    // ROLE
    else if (typeSubmitGroup === "role") {
      if (typeSubmit === "addNew") {
        return <AddNewRoleForm onSubmit={onSubmit} />;
      } else if (typeSubmit === "update") {
        return <UpdateRoleForm onSubmit={onSubmit} roleDetails={roleDetails} />;
      } else if (typeSubmit === "delete") {
        return (
          <DeleteRoleForm
            onSubmit={onSubmit}
            onNoSubmit={closeModal}
            roleDetails={roleDetails}
          />
        );
      }
    }
    // CATEGORY
    else if (typeSubmitGroup === "category") {
      if (typeSubmit === "addNew") {
        return <AddNewCategoryForm onSubmit={onSubmit} />;
      } else if (typeSubmit === "update") {
        return (
          <UpdateCategoryForm
            onSubmit={onSubmit}
            categoryDetails={categoryDetails}
          />
        );
      } else if (typeSubmit === "delete") {
        return (
          <DeleteCategoryForm
            onSubmit={onSubmit}
            onNoSubmit={closeModal}
            categoryDetails={categoryDetails}
          />
        );
      }
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
