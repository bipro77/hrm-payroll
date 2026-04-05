import '@coreui/coreui/dist/css/coreui.min.css';
import { useEffect, useState } from "react";
import API from "../../api/axios";
import {
  CCard,
  CCardBody,
  CCardHeader,
  CTable,
  CTableHead,
  CTableRow,
  CTableHeaderCell,
  CTableBody,
  CTableDataCell,
  CButton,
  CSpinner,
} from "@coreui/react";

interface Employee {
  id: number;
  name: string;
  email: string;
}

export default function EmployeePage() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [page, setPage] = useState(0);
  const [total, setTotal] = useState(0);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const size = 5;

  useEffect(() => {
    setLoading(true);
    setError("");

    API.get("/employees", {
      params: {
        page,
        size,
        sortBy: "id",
      },
    })
      .then((res) => {
        setEmployees(res.data.content);
        setTotal(res.data.totalElements);
      })
      .catch((err) => {
        console.error(err);
        setError("Failed to load employees");
      })
      .finally(() => {
        setLoading(false);
      });
  }, [page]);

  return (
    <CCard>
      <CCardHeader>
        <strong>Employee Management</strong>
      </CCardHeader>

      <CCardBody>
        <p>Total Employees: {total}</p>

        {/* Loading */}
        {loading && <CSpinner color="primary" />}

        {/* Error */}
        {error && <p style={{ color: "red" }}>{error}</p>}

        {/* Table */}
        {!loading && !error && (
          <>
            <CTable hover bordered>
              <CTableHead>
                <CTableRow>
                  <CTableHeaderCell>ID</CTableHeaderCell>
                  <CTableHeaderCell>Name</CTableHeaderCell>
                  <CTableHeaderCell>Email</CTableHeaderCell>
                  <CTableHeaderCell>Actions</CTableHeaderCell>
                </CTableRow>
              </CTableHead>

              <CTableBody>
                {employees.map((emp) => (
                  <CTableRow key={emp.id}>
                    <CTableDataCell>{emp.id}</CTableDataCell>
                    <CTableDataCell>{emp.name}</CTableDataCell>
                    <CTableDataCell>{emp.email}</CTableDataCell>
                    <CTableDataCell>
                      <CButton size="sm" color="info" className="me-2">
                        Edit
                      </CButton>
                      <CButton size="sm" color="danger">
                        Delete
                      </CButton>
                    </CTableDataCell>
                  </CTableRow>
                ))}
              </CTableBody>
            </CTable>

            {/* Pagination */}
            <div className="d-flex justify-content-between mt-3">
              <CButton
                color="secondary"
                disabled={page === 0}
                onClick={() => setPage((p) => p - 1)}
              >
                Prev
              </CButton>

              <span>Page: {page + 1}</span>

              <CButton
                color="secondary"
                disabled={(page + 1) * size >= total}
                onClick={() => setPage((p) => p + 1)}
              >
                Next
              </CButton>
            </div>
          </>
        )}
      </CCardBody>
    </CCard>
  );
}