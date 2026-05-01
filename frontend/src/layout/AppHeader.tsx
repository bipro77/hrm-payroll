import { NavLink, useNavigate } from "react-router-dom";
import {
  CButton,
  CContainer,
  CHeader,
  CHeaderNav,
  CHeaderToggler,
  CNavItem,
  CNavLink,
} from "@coreui/react";

interface AppHeaderProps {
  onSidebarToggle: () => void;
}

const AppHeader = ({ onSidebarToggle }: AppHeaderProps) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login", { replace: true });
  };

  return (
    <CHeader position="sticky" className="app-header mb-4 p-0 border-bottom">
      <CContainer fluid className="px-4">
        <CHeaderToggler
          className="me-3"
          aria-label="Toggle sidebar"
          onClick={onSidebarToggle}
        >
          <span className="navbar-toggler-icon" />
        </CHeaderToggler>

        <CHeaderNav>
          <CNavItem>
            <CNavLink to="/employees" as={NavLink}>
              Employees
            </CNavLink>
          </CNavItem>
        </CHeaderNav>

        <CHeaderNav className="ms-auto">
          <CButton color="secondary" variant="outline" size="sm" onClick={handleLogout}>
            Logout
          </CButton>
        </CHeaderNav>
      </CContainer>
    </CHeader>
  );
};

export default AppHeader;
