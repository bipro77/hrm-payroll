import { useEffect, useState } from "react";
import API from "../../api/axios";
import {
  CAlert,
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
  CFormInput,
  CFormLabel,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CSpinner,
} from "@coreui/react";

interface Employee {
  id: number;
  name: string;
  email: string;
  department?: string;
  designation?: string;
  salary?: number;
}

type EmployeeForm = Omit<Employee, "id">;

const emptyForm: EmployeeForm = {
  name: "",
  email: "",
  department: "",
  designation: "",
  salary: 0,
};

export default function EmployeePage() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [page, setPage] = useState(0);
  const [total, setTotal] = useState(0);

  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [deletingId, setDeletingId] = useState<number | null>(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [editEmployee, setEditEmployee] = useState<Employee | null>(null);
  const [form, setForm] = useState<EmployeeForm>(emptyForm);

  const size = 5;

  const loadEmployees = () => {
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
  };

  useEffect(() => {
    loadEmployees();
  }, [page]);

  const openEditModal = (employee: Employee) => {
    setSuccess("");
    setError("");
    setEditEmployee(employee);
    setForm({
      name: employee.name,
      email: employee.email,
      department: employee.department ?? "",
      designation: employee.designation ?? "",
      salary: employee.salary ?? 0,
    });
  };

  const closeEditModal = () => {
    setEditEmployee(null);
    setForm(emptyForm);
  };

  const updateForm = (field: keyof EmployeeForm, value: string) => {
    setForm((current) => ({
      ...current,
      [field]: field === "salary" ? Number(value) : value,
    }));
  };

  const handleUpdate = async () => {
    if (!editEmployee) {
      return;
    }

    setSaving(true);
    setError("");
    setSuccess("");

    try {
      const res = await API.put(`/employees/${editEmployee.id}`, form);

      setEmployees((current) =>
        current.map((employee) =>
          employee.id === editEmployee.id ? res.data : employee
        )
      );
      setSuccess("Employee updated successfully");
      closeEditModal();
    } catch (err: any) {
      console.error(err);
      setError(err?.response?.data?.message || "Failed to update employee");
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (employee: Employee) => {
    const confirmed = window.confirm(`Delete ${employee.name}?`);

    if (!confirmed) {
      return;
    }

    setDeletingId(employee.id);
    setError("");
    setSuccess("");

    try {
      await API.delete(`/employees/${employee.id}`);
      setSuccess("Employee deleted successfully");

      if (employees.length === 1 && page > 0) {
        setPage((current) => current - 1);
      } else {
        loadEmployees();
      }
    } catch (err: any) {
      console.error(err);
      setError(err?.response?.data?.message || "Failed to delete employee");
    } finally {
      setDeletingId(null);
    }
  };

  return (
    <>
      <CCard>
        <CCardHeader className="d-flex align-items-center justify-content-between">
          <strong>Employee Management</strong>
          <span>Total Employees: {total}</span>
        </CCardHeader>

        <CCardBody>
          {success && <CAlert color="success">{success}</CAlert>}
          {error && <CAlert color="danger">{error}</CAlert>}

          {loading && <CSpinner color="primary" />}

          {!loading && (
            <>
              <CTable hover bordered responsive>
                <CTableHead>
                  <CTableRow>
                    <CTableHeaderCell>ID</CTableHeaderCell>
                    <CTableHeaderCell>Name</CTableHeaderCell>
                    <CTableHeaderCell>Email</CTableHeaderCell>
                    <CTableHeaderCell>Department</CTableHeaderCell>
                    <CTableHeaderCell>Designation</CTableHeaderCell>
                    <CTableHeaderCell>Salary</CTableHeaderCell>
                    <CTableHeaderCell>Actions</CTableHeaderCell>
                  </CTableRow>
                </CTableHead>

                <CTableBody>
                  {employees.map((emp) => (
                    <CTableRow key={emp.id}>
                      <CTableDataCell>{emp.id}</CTableDataCell>
                      <CTableDataCell>{emp.name}</CTableDataCell>
                      <CTableDataCell>{emp.email}</CTableDataCell>
                      <CTableDataCell>{emp.department || "-"}</CTableDataCell>
                      <CTableDataCell>{emp.designation || "-"}</CTableDataCell>
                      <CTableDataCell>{emp.salary ?? "-"}</CTableDataCell>
                      <CTableDataCell>
                        <CButton
                          size="sm"
                          color="info"
                          className="me-2"
                          onClick={() => openEditModal(emp)}
                        >
                          Edit
                        </CButton>
                        <CButton
                          size="sm"
                          color="danger"
                          disabled={deletingId === emp.id}
                          onClick={() => handleDelete(emp)}
                        >
                          {deletingId === emp.id ? "Deleting..." : "Delete"}
                        </CButton>
                      </CTableDataCell>
                    </CTableRow>
                  ))}
                </CTableBody>
              </CTable>

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

      <CModal visible={Boolean(editEmployee)} onClose={closeEditModal}>
        <CModalHeader>
          <CModalTitle>Edit Employee</CModalTitle>
        </CModalHeader>
        <CModalBody>
          <div className="mb-3">
            <CFormLabel htmlFor="employee-name">Name</CFormLabel>
            <CFormInput
              id="employee-name"
              value={form.name}
              onChange={(event) => updateForm("name", event.target.value)}
            />
          </div>

          <div className="mb-3">
            <CFormLabel htmlFor="employee-email">Email</CFormLabel>
            <CFormInput
              id="employee-email"
              type="email"
              value={form.email}
              onChange={(event) => updateForm("email", event.target.value)}
            />
          </div>

          <div className="mb-3">
            <CFormLabel htmlFor="employee-department">Department</CFormLabel>
            <CFormInput
              id="employee-department"
              value={form.department}
              onChange={(event) => updateForm("department", event.target.value)}
            />
          </div>

          <div className="mb-3">
            <CFormLabel htmlFor="employee-designation">Designation</CFormLabel>
            <CFormInput
              id="employee-designation"
              value={form.designation}
              onChange={(event) => updateForm("designation", event.target.value)}
            />
          </div>

          <div>
            <CFormLabel htmlFor="employee-salary">Salary</CFormLabel>
            <CFormInput
              id="employee-salary"
              type="number"
              min={0}
              value={form.salary}
              onChange={(event) => updateForm("salary", event.target.value)}
            />
          </div>
        </CModalBody>
        <CModalFooter>
          <CButton color="secondary" variant="outline" onClick={closeEditModal}>
            Cancel
          </CButton>
          <CButton color="primary" disabled={saving} onClick={handleUpdate}>
            {saving ? "Saving..." : "Save changes"}
          </CButton>
        </CModalFooter>
      </CModal>
    </>
  );
}
