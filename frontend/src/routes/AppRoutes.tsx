import { Routes, Route, Navigate } from "react-router-dom";
import EmployeePage from "../views/employees/EmployeePage";
import LoginPage from "../views/auth/LoginPage";

/**
 * Simple auth check
 */
const isAuthenticated = () => {
  return !!localStorage.getItem("token");
};

/**
 * Protected Route wrapper
 */
const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  return isAuthenticated() ? children : <Navigate to="/login" replace />;
};

const AppRoutes = () => {
  return (
    <Routes>

      {/* Default route */}
      <Route
        path="/"
        element={
          isAuthenticated()
            ? <Navigate to="/employees" replace />
            : <Navigate to="/login" replace />
        }
      />

      {/* Login */}
      <Route path="/login" element={<LoginPage />} />

      {/* Protected route */}
      <Route
        path="/employees"
        element={
          <ProtectedRoute>
            <EmployeePage />
          </ProtectedRoute>
        }
      />

      {/* Fallback (ALWAYS LAST) */}
      <Route path="*" element={<h1>404 Not Found</h1>} />

    </Routes>
  );
};

export default AppRoutes;