ALTER TABLE employees
    ADD CONSTRAINT employees_user_id_key UNIQUE (user_id);

ALTER TABLE departments
    ADD CONSTRAINT uq_department_name_location UNIQUE (name, location_id);

ALTER TABLE shift_assignments
    ADD CONSTRAINT uq_shift_employee UNIQUE (shift_id, employee_id);

ALTER TABLE users
    ADD CONSTRAINT users_email_key UNIQUE (email);

CREATE INDEX idx_audit_logs_action ON audit_logs (action);

CREATE INDEX idx_audit_logs_entity_id ON audit_logs (entity_type, entity_id);

CREATE INDEX idx_audit_logs_entity_type ON audit_logs (entity_type);

CREATE INDEX idx_audit_logs_timestamp ON audit_logs (timestamp);

CREATE INDEX idx_availability_date_range ON availability (start_date, end_date);

CREATE INDEX idx_availability_day ON availability (day_of_week);

CREATE INDEX idx_availability_type ON availability (availability_type);

CREATE INDEX idx_employees_active ON employees (is_active);

CREATE INDEX idx_employees_employment_type ON employees (employment_type);

CREATE INDEX idx_employees_skills ON employees (skills);

CREATE INDEX idx_leave_requests_date_range ON leave_requests (start_date, end_date);

CREATE INDEX idx_leave_requests_status ON leave_requests (status);

CREATE INDEX idx_locations_active ON locations (is_active);

CREATE INDEX idx_notifications_created_at ON notifications (created_at);

CREATE INDEX idx_notifications_is_read ON notifications (is_read);

CREATE INDEX idx_notifications_recipient_unread ON notifications (recipient_id, is_read);

CREATE INDEX idx_shift_assignments_status ON shift_assignments (status);

CREATE INDEX idx_shifts_date ON shifts (shift_date);

CREATE INDEX idx_shifts_date_location ON shifts (shift_date, location_id);

CREATE INDEX idx_shifts_status ON shifts (status);

CREATE INDEX idx_swap_requests_status ON swap_requests (status);

CREATE INDEX idx_users_active ON users (is_active);

CREATE INDEX idx_users_role ON users (role);

ALTER TABLE audit_logs
    ADD CONSTRAINT audit_logs_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES users (id) ON DELETE SET NULL;

CREATE INDEX idx_audit_logs_actor ON audit_logs (actor_id);

ALTER TABLE availability
    ADD CONSTRAINT availability_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE;

CREATE INDEX idx_availability_employee ON availability (employee_id);

ALTER TABLE departments
    ADD CONSTRAINT departments_location_id_fkey FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE RESTRICT;

CREATE INDEX idx_departments_location ON departments (location_id);

ALTER TABLE employees
    ADD CONSTRAINT employees_department_id_fkey FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE RESTRICT;

CREATE INDEX idx_employees_department ON employees (department_id);

ALTER TABLE employees
    ADD CONSTRAINT employees_primary_location_id_fkey FOREIGN KEY (primary_location_id) REFERENCES locations (id) ON DELETE RESTRICT;

CREATE INDEX idx_employees_location ON employees (primary_location_id);

ALTER TABLE employees
    ADD CONSTRAINT employees_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT;

ALTER TABLE leave_requests
    ADD CONSTRAINT leave_requests_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE RESTRICT;

CREATE INDEX idx_leave_requests_employee ON leave_requests (employee_id);

ALTER TABLE leave_requests
    ADD CONSTRAINT leave_requests_reviewed_by_fkey FOREIGN KEY (reviewed_by) REFERENCES users (id) ON DELETE SET NULL;

CREATE INDEX idx_leave_requests_reviewer ON leave_requests (reviewed_by);

ALTER TABLE manager_locations
    ADD CONSTRAINT manager_locations_location_id_fkey FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE;

CREATE INDEX idx_manager_locations_location ON manager_locations (location_id);

ALTER TABLE manager_locations
    ADD CONSTRAINT manager_locations_manager_id_fkey FOREIGN KEY (manager_id) REFERENCES users (id) ON DELETE CASCADE;

CREATE INDEX idx_manager_locations_manager ON manager_locations (manager_id);

ALTER TABLE notifications
    ADD CONSTRAINT notifications_recipient_id_fkey FOREIGN KEY (recipient_id) REFERENCES employees (id) ON DELETE CASCADE;

CREATE INDEX idx_notifications_recipient ON notifications (recipient_id);

ALTER TABLE shift_assignments
    ADD CONSTRAINT shift_assignments_assigned_by_fkey FOREIGN KEY (assigned_by) REFERENCES users (id) ON DELETE RESTRICT;

CREATE INDEX idx_shift_assignments_assigned_by ON shift_assignments (assigned_by);

ALTER TABLE shift_assignments
    ADD CONSTRAINT shift_assignments_employee_id_fkey FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE RESTRICT;

CREATE INDEX idx_shift_assignments_employee ON shift_assignments (employee_id);

ALTER TABLE shift_assignments
    ADD CONSTRAINT shift_assignments_shift_id_fkey FOREIGN KEY (shift_id) REFERENCES shifts (id) ON DELETE RESTRICT;

CREATE INDEX idx_shift_assignments_shift ON shift_assignments (shift_id);

ALTER TABLE shifts
    ADD CONSTRAINT shifts_created_by_fkey FOREIGN KEY (created_by) REFERENCES users (id) ON DELETE RESTRICT;

CREATE INDEX idx_shifts_created_by ON shifts (created_by);

ALTER TABLE shifts
    ADD CONSTRAINT shifts_department_id_fkey FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE RESTRICT;

CREATE INDEX idx_shifts_department ON shifts (department_id);

ALTER TABLE shifts
    ADD CONSTRAINT shifts_location_id_fkey FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE RESTRICT;

CREATE INDEX idx_shifts_location ON shifts (location_id);

ALTER TABLE swap_requests
    ADD CONSTRAINT swap_requests_requested_by_fkey FOREIGN KEY (requested_by) REFERENCES employees (id) ON DELETE RESTRICT;

CREATE INDEX idx_swap_requests_requester ON swap_requests (requested_by);

ALTER TABLE swap_requests
    ADD CONSTRAINT swap_requests_requester_assignment_id_fkey FOREIGN KEY (requester_assignment_id) REFERENCES shift_assignments (id) ON DELETE RESTRICT;

ALTER TABLE swap_requests
    ADD CONSTRAINT swap_requests_reviewed_by_fkey FOREIGN KEY (reviewed_by) REFERENCES users (id) ON DELETE SET NULL;

CREATE INDEX idx_swap_requests_reviewer ON swap_requests (reviewed_by);

ALTER TABLE swap_requests
    ADD CONSTRAINT swap_requests_target_assignment_id_fkey FOREIGN KEY (target_assignment_id) REFERENCES shift_assignments (id) ON DELETE RESTRICT;

ALTER TABLE swap_requests
    ADD CONSTRAINT swap_requests_target_employee_id_fkey FOREIGN KEY (target_employee_id) REFERENCES employees (id) ON DELETE RESTRICT;

CREATE INDEX idx_swap_requests_target ON swap_requests (target_employee_id);