import { Routes, Route, Navigate } from "react-router-dom";
import EmployeePage from "../views/employees/EmployeePage";
import LoginPage from "../views/auth/LoginPage";
import DefaultLayout from "../layout/DefaultLayout";
import ProtectedRoute from "./ProtectedRoute";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />

      <Route
        path="/"
        element={
          <ProtectedRoute>
            <DefaultLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Navigate to="/employees" replace />} />
        <Route path="employees" element={<EmployeePage />} />
      </Route>

      <Route path="*" element={<h1>404 Not Found</h1>} />
    </Routes>
  );
};

export default AppRoutes;
