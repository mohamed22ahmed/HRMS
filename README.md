HRMS MICROSERVICES - RECOMMENDED ARCHITECTURE
================================================

ARCHITECTURE OVERVIEW
---------------------

Recommended core services: 10

1. Identity & Access Management
2. Organization & Employee Management
3. Scheduling & Holiday Management
4. Time & Attendance Management
5. Leave & Vacation Management
6. Payroll & Compensation
7. Recruitment & Onboarding
8. Asset & Inventory Management
9. Performance & Talent Management
10. Workflow, Notifications & Documents

GENERAL ARCHITECTURE RULES

- Each service owns its own database.
- Services must not directly access another service's database.
- Use UUIDs for references between services.
- Cross-service references are logical references, not database foreign keys.
- Tenant-owned business records should include tenant_id.
- Every employee has an identity account, but not every user must be an employee.
- Each service should be internally modular.
- Use REST APIs for synchronous communication and events for asynchronous communication.
- Avoid splitting services further unless there is a clear business or technical reason.

================================================
1. IDENTITY & ACCESS MANAGEMENT SERVICE
================================================

SERVICE NAME:
identity-service

RESPONSIBILITIES:

- Authentication and authorization.
- User account management.
- Tenant management.
- Login and logout.
- JWT access-token generation.
- Refresh-token management.
- Password hashing and reset.
- Role-based access control (RBAC).
- Permission management.
- Platform administrator management.
- Tenant administrator management.
- Account activation and deactivation.
- Security-related audit events.

TABLES:

Tenant management:
- tenants

User management:
- users
- user_sessions
- refresh_tokens
- password_reset_tokens
- email_verification_tokens
- login_attempts

Authorization:
- roles
- permissions
- user_roles
- role_permissions

Optional:
- user_tenants
- audit_events

DB_NAME:
hrms_identity

IMPORTANT NOTES:

- Identity owns authentication information, credentials, roles, and permissions.
- Do not store employee profile information in the users table.
- employee-service stores user_id as a logical reference to identity.users.id.
- Do not create a cross-database foreign key between users and employees.
- Every employee has a user account, but other users may exist without being employees.
- Platform administrators may have no tenant_id.
- Tenant users must be associated with their tenant.
- Add user_tenants only if one account needs access to multiple tenants.
- Passwords must be stored using a secure password hashing algorithm.
- Every protected service must validate JWTs and enforce authorization.

----------------------------------


================================================
2. ORGANIZATION & EMPLOYEE MANAGEMENT SERVICE
================================================

SERVICE NAME:
employee-service

RESPONSIBILITIES:

- Manage company organization structure.
- Manage branches and locations.
- Manage departments.
- Manage positions and job titles.
- Create and manage employees.
- Link employees to identity accounts.
- Manage employee personal information.
- Manage employee professional information.
- Manage employee reporting relationships.
- Manage employment contracts.
- Track employment status and history.
- Manage employee documents and emergency contacts.

TABLES:

Organization:
- branches
- departments
- positions
- job_titles (optional)

Employee information:
- employees
- employee_contacts
- employee_addresses
- employee_emergency_contacts
- employee_documents
- employee_education
- employee_experience
- employee_dependents
- employee_notes

Employment:
- employment_contracts
- employment_status_history
- employee_reporting_history

DB_NAME:
hrms_employee

IMPORTANT NOTES:

- employees must contain tenant_id.
- employees must contain user_id UUID referencing identity.users.id.
- user_id should have a UNIQUE constraint because each employee has one identity account.
- Do not create a cross-database foreign key to identity.users.
- Do not create a JPA relationship between entities in different services.
- department_id, position_id, branch_id, and manager_id can have normal foreign keys because they belong to this service.
- Use tenant-scoped uniqueness where appropriate, for example:
  UNIQUE (tenant_id, employee_number).
- Do not duplicate user credentials, passwords, or roles here.
- Employee termination should not delete the identity account. Deactivate the account and preserve employee history.

----------------------------------


================================================
3. SCHEDULING & HOLIDAY MANAGEMENT SERVICE
================================================

SERVICE NAME:
scheduling-service

RESPONSIBILITIES:

- Manage company working schedules.
- Define working days and non-working days.
- Manage shifts.
- Define shift start and end times.
- Manage break periods.
- Assign shifts to employees.
- Assign work schedules to employees.
- Manage shift rotations.
- Manage shift exchange requests.
- Manage public holidays.
- Manage company holidays.
- Manage holiday calendars.
- Manage holiday calendar assignments.
- Define special working days.
- Track employees scheduled to work during holidays.

TABLES:

Work schedules:
- work_schedules
- work_schedule_days
- work_schedule_assignments

Shifts:
- shifts
- shift_days
- shift_breaks
- shift_assignments
- shift_rotations
- shift_rotation_assignments
- shift_swap_requests

Holiday management:
- holiday_calendars
- holidays
- holiday_calendar_assignments
- special_working_days

Optional:
- holiday_work_schedules

DB_NAME:
hrms_scheduling

IMPORTANT NOTES:

- This service defines when employees are expected to work.
- It does not store actual clock-in or clock-out records.
- It does not calculate employee leave balances.
- employee_id should be stored as a logical reference to employee-service.
- Do not create cross-database foreign keys to employees.
- Include tenant_id on tenant-owned schedules, shifts, and holiday records.
- A holiday calendar can be assigned to a branch, group, or employee.
- Support different working schedules for different departments and employees.
- Support rotating shifts and overnight shifts.
- Time zones should be considered when defining shifts and working schedules.
- attendance-service can consume schedule and holiday information through APIs or events.
- Avoid duplicating employee and branch information.

----------------------------------


================================================
4. TIME & ATTENDANCE MANAGEMENT SERVICE
================================================

SERVICE NAME:
attendance-service

RESPONSIBILITIES:

- Employee clock-in and clock-out.
- Track actual working hours.
- Manage attendance records.
- Track late arrivals.
- Track early departures.
- Track absences.
- Manage attendance corrections.
- Manage attendance adjustments.
- Manage timesheets.
- Calculate working hours.
- Manage overtime requests.
- Calculate overtime hours.
- Provide approved attendance and overtime information to payroll.

TABLES:

Attendance:
- attendance_records
- attendance_events
- attendance_adjustments
- attendance_corrections
- attendance_daily_summaries

Timesheets:
- timesheets
- timesheet_entries

Overtime:
- overtime_rules
- overtime_requests
- overtime_records
- overtime_approvals

Optional:
- attendance_devices
- attendance_device_logs
- attendance_import_jobs

DB_NAME:
hrms_attendance

IMPORTANT NOTES:

- This service records when employees actually work.
- Scheduling-service defines expected working hours; attendance-service records actual working hours.
- Store employee_id as a logical reference to employee-service.
- Do not create cross-database foreign keys.
- Include tenant_id on tenant-owned attendance records.
- Use schedule and holiday information from scheduling-service when calculating attendance.
- Keep raw attendance events separate from calculated attendance records.
- Do not overwrite original clock events when correcting attendance.
- Keep an audit trail for attendance corrections.
- Overtime should be approved according to company policy before being sent to payroll.
- Payroll should consume approved attendance and overtime information through APIs or events.

----------------------------------


================================================
5. LEAVE & VACATION MANAGEMENT SERVICE
================================================

SERVICE NAME:
leave-service

RESPONSIBILITIES:

- Manage vacation types.
- Manage leave types.
- Define leave policies.
- Define employee vacation entitlements.
- Calculate leave accrual.
- Manage leave balances.
- Manage annual vacation balances.
- Manage sick leave.
- Manage emergency leave.
- Manage unpaid leave.
- Manage maternity and paternity leave.
- Manage leave requests.
- Manage leave approvals.
- Manage leave cancellation.
- Manage leave carryover.
- Manage leave expiration.
- Manage leave balance adjustments.
- Track leave history.
- Provide approved leave information to attendance and payroll.

TABLES:

Leave types and policies:
- leave_types
- leave_type_rules
- leave_policies
- leave_policy_rules
- leave_policy_assignments

Leave balances:
- leave_balances
- leave_transactions
- leave_accrual_rules
- leave_accrual_records
- leave_carryover_rules
- leave_balance_adjustments

Leave requests:
- leave_requests
- leave_request_days
- leave_request_approvals
- leave_request_attachments
- leave_cancellations
- leave_request_history

Optional:
- leave_blackout_periods
- employee_leave_calendar

DB_NAME:
hrms_leave

IMPORTANT NOTES:

- Annual vacation, sick leave, emergency leave, unpaid leave, and other leave types belong to this service.
- Do not create a separate microservice for vacations.
- employee_id should be stored as a logical reference to employee-service.
- Do not create cross-database foreign keys to employees.
- Include tenant_id on tenant-owned leave records.
- Leave policies should support different employee groups and employment conditions.
- Support full-day, half-day, and hourly leave where required.
- Leave balances should be maintained through transactions, not unexplained direct updates.
- A submitted request should not automatically be treated as approved leave.
- Handle request cancellation and rejection without corrupting leave balances.
- Support leave carryover and expiration rules.
- Use scheduling-service holiday calendars to calculate working days when applicable.
- Public holidays and weekly non-working days should not normally consume annual leave.
- Attendance-service should know about approved leave when calculating expected attendance.
- Payroll-service should receive unpaid leave information for salary calculations.
- Keep country-specific legal rules configurable.

----------------------------------


================================================
6. PAYROLL & COMPENSATION SERVICE
================================================

SERVICE NAME:
payroll-service

RESPONSIBILITIES:

- Salary structure management.
- Employee compensation management.
- Salary history management.
- Income and earning management.
- Allowance management.
- Deduction management.
- Payroll period management.
- Payroll calculations.
- Payroll approval and processing.
- Payslip generation.
- Tax management.
- Social insurance management.
- Payment batch and payment status tracking.

TABLES:

Salary structure:
- salary_structures
- salary_components
- employee_compensation
- compensation_history

Income and earnings:
- income_types
- employee_income
- income_history

Deductions:
- deduction_types
- employee_deductions
- deduction_history
- deduction_installments

Payroll processing:
- payroll_periods
- payroll_runs
- payroll_run_employees
- payroll_items
- payroll_adjustments
- payslips

Taxes and insurance:
- tax_rules
- employee_tax_information
- social_insurance_rules

Payments:
- payment_batches
- payroll_payments

DB_NAME:
hrms_payroll

IMPORTANT NOTES:

- Payroll is highly sensitive and requires strict access permissions.
- Audit all payroll changes and processing actions.
- Tax and social insurance rules must support the applicable country.
- Do not directly query employee-service, attendance-service, or leave-service databases.
- Use APIs or events to obtain employee, attendance, overtime, and leave information.
- Store payroll calculation results for historical accuracy.
- Do not recalculate old payroll using current salary rules.
- Use precise decimal types for monetary values, never floating-point types.
- Keep currency information with monetary records.
- Use salary_components as a reusable catalog of earning and deduction components where possible.
- Component types can include EARNING, DEDUCTION, and EMPLOYER_CONTRIBUTION.
- Calculation types can include FIXED, PERCENTAGE, FORMULA, and PER_UNIT.
- Employer contributions should not reduce employee net salary.
- Examples of earnings include basic salary, housing allowance, transportation allowance, overtime, bonuses, and commissions.
- Examples of deductions include taxes, social insurance, loan repayments, unpaid leave deductions, and absence deductions.

----------------------------------


================================================
7. RECRUITMENT & ONBOARDING SERVICE
================================================

SERVICE NAME:
recruitment-service

RESPONSIBILITIES:

- Job requisition management.
- Job opening management.
- Candidate management.
- Candidate application tracking.
- Recruitment stage management.
- Interview scheduling.
- Interview feedback management.
- Job offer management.
- Offer approvals.
- Employee onboarding.
- Onboarding task management.
- Coordinate hiring with identity-service and employee-service.

TABLES:

Recruitment:
- job_requisitions
- job_openings
- candidates
- candidate_contacts
- candidate_documents
- applications
- application_stage_history

Interviews:
- interview_rounds
- interviews
- interview_feedback

Job offers:
- job_offers
- offer_approvals

Onboarding:
- onboarding_templates
- onboarding_cases
- onboarding_tasks
- onboarding_task_assignments

DB_NAME:
hrms_recruitment

IMPORTANT NOTES:

- Candidates are not employees.
- Candidate information must remain separate from employee information.
- When a candidate is hired, create an identity account and an employee record.
- Link the new employee to the identity account using employees.user_id.
- Handle failures during account and employee creation using retries, compensation, or a reliable orchestration workflow.
- Candidate documents may use the document storage capability from platform-service.
- Keep recruitment stages configurable where possible.

----------------------------------


================================================
8. ASSET & INVENTORY MANAGEMENT SERVICE
================================================

SERVICE NAME:
asset-service

RESPONSIBILITIES:

- Company asset management.
- Asset categorization.
- Asset registration.
- Asset assignment to employees.
- Asset return tracking.
- Asset assignment history.
- Asset maintenance.
- Asset transfer management.
- Asset disposal.
- Inventory and consumable management.
- Supplier management if procurement is not separate.

TABLES:

Asset management:
- asset_categories
- assets
- asset_assignments
- asset_assignment_history

Asset operations:
- asset_maintenance
- asset_transfers
- asset_disposals

Inventory:
- inventory_items
- inventory_transactions
- inventory_stock

Suppliers:
- suppliers

DB_NAME:
hrms_asset

IMPORTANT NOTES:

- Store employee_id as a logical reference to employee-service.
- Do not create a cross-database foreign key to employees.
- Include tenant_id on tenant-owned assets and transactions.
- Use location_id as a logical reference if branches and locations are owned by employee-service.
- Do not duplicate branch information.
- Asset assignments should preserve assignment and return history.
- Inventory stock can be calculated from inventory transactions; maintain a stock table only if needed for performance.
- Use asset codes and serial numbers to identify assets.

----------------------------------


================================================
9. PERFORMANCE & TALENT MANAGEMENT SERVICE
================================================

SERVICE NAME:
talent-service

RESPONSIBILITIES:

- Performance cycle management.
- Performance review management.
- Performance template management.
- Employee goal management.
- Goal progress tracking.
- Competency management.
- Employee development plans.
- Training course management.
- Training session management.
- Employee training enrollment.
- Training completion tracking.
- Certification management.

TABLES:

Performance:
- performance_cycles
- performance_templates
- performance_template_sections
- performance_reviews
- performance_reviewers
- performance_ratings

Goals:
- goals
- goal_updates

Competencies:
- competencies
- employee_competencies

Development:
- development_plans
- development_plan_items

Training:
- training_courses
- training_sessions
- training_enrollments
- training_completions
- certifications

DB_NAME:
hrms_talent

IMPORTANT NOTES:

- Performance and training are merged initially because they both support employee development.
- Keep performance and training as separate internal modules.
- Store employee_id as a logical reference to employee-service.
- Do not create cross-database foreign keys.
- Include tenant_id on tenant-owned records.
- If training grows into a full Learning Management System (LMS), it can become a separate service.

----------------------------------


================================================
10. WORKFLOW, NOTIFICATIONS & DOCUMENTS SERVICE
================================================

SERVICE NAME:
platform-service

RESPONSIBILITIES:

- Reusable workflow management.
- Approval workflow definitions.
- Workflow execution and tracking.
- Approval task management.
- In-app notifications.
- Email notifications.
- Notification templates.
- Notification preferences.
- Notification delivery tracking.
- Document metadata management.
- Document version management.
- Integration with file/object storage.
- Generated document tracking.

TABLES:

Workflow:
- workflow_definitions
- workflow_versions
- workflow_steps
- workflow_instances
- workflow_tasks
- workflow_approvals

Notifications:
- notification_templates
- notifications
- notification_preferences
- notification_deliveries

Documents:
- document_metadata
- document_versions
- document_access_rules
- generated_documents

DB_NAME:
hrms_platform

IMPORTANT NOTES:

- Workflow records should reference business entities using entity_type and entity_id.
- Do not create cross-service foreign keys to business entities.
- Store actual files in object storage, not directly in database tables unless there is a specific reason.
- Store file metadata, storage keys, and version information in this service.
- Notifications should support multiple channels, such as in-app and email.
- Workflow definitions should be reusable across employee, leave, payroll, recruitment, and other modules.
- If the platform service becomes too broad, workflow, notifications, and documents can be split later.

----------------------------------


================================================
OPTIONAL SERVICES TO ADD LATER
================================================

1. EXPENSES & REIMBURSEMENTS SERVICE

RESPONSIBILITIES:
- Employee expense claims.
- Expense policy management.
- Expense approval.
- Reimbursement tracking.

TABLES:
- expense_categories
- expense_policies
- expense_claims
- expense_claim_items
- expense_receipts
- expense_approvals
- reimbursements

DB_NAME:
hrms_expenses

IMPORTANT NOTES:
- Can be added after the core HRMS workflows are stable.
- May integrate with payroll or finance for reimbursements.

----------------------------------

2. TRAVEL & BUSINESS TRIPS SERVICE

RESPONSIBILITIES:
- Travel request management.
- Trip management.
- Booking management.
- Per diem calculation.
- Trip expense settlement.

TABLES:
- travel_requests
- travel_request_approvals
- trips
- trip_segments
- travel_bookings
- per_diem_rules
- trip_expenses
- trip_settlements

DB_NAME:
hrms_travel

IMPORTANT NOTES:
- Can be integrated with expenses and reimbursements.
- Add only when business travel is required.

----------------------------------

3. SURVEYS & EMPLOYEE ENGAGEMENT SERVICE

RESPONSIBILITIES:
- Employee surveys.
- Survey distribution.
- Survey responses.
- Engagement campaigns.

TABLES:
- surveys
- survey_questions
- survey_options
- survey_assignments
- survey_responses
- survey_answers
- engagement_campaigns

DB_NAME:
hrms_engagement

IMPORTANT NOTES:
- Support anonymous surveys where required.
- Apply tenant-level data isolation.
- Restrict access to sensitive survey results.

----------------------------------

4. REPORTING & ANALYTICS SERVICE

RESPONSIBILITIES:
- Reporting.
- Dashboards.
- Scheduled reports.
- Data exports.
- Analytics read models.

TABLES:
- report_definitions
- report_schedules
- report_runs
- dashboard_definitions
- analytics_snapshots
- data_export_jobs

DB_NAME:
hrms_analytics

IMPORTANT NOTES:
- Prefer read models populated through events or ETL.
- Do not directly query all service databases.
- Apply tenant isolation to reports and analytics data.

----------------------------------

5. INTEGRATION SERVICE

RESPONSIBILITIES:
- Third-party integrations.
- Webhook management.
- External data synchronization.
- Integration configuration.
- Integration execution logs.

TABLES:
- integration_connections
- integration_credentials
- integration_mappings
- webhook_subscriptions
- webhook_deliveries
- external_sync_jobs
- external_sync_logs

DB_NAME:
hrms_integrations

IMPORTANT NOTES:
- Store credentials securely.
- Never store API secrets as plain text.
- Support retries and failure tracking.
- Use webhooks or message queues for asynchronous integrations.

----------------------------------

6. PROCUREMENT SERVICE

RESPONSIBILITIES:
- Purchase requisitions.
- Purchase orders.
- Vendor management.
- Goods receipt tracking.
- Purchasing approvals.

TABLES:
- purchase_requisitions
- purchase_requisition_items
- purchase_orders
- purchase_order_items
- goods_receipts
- vendors
- vendor_contacts

DB_NAME:
hrms_procurement

IMPORTANT NOTES:
- If procurement is small, it can initially be part of asset-service.
- Split it into its own service when purchasing workflows become substantial.

----------------------------------

7. DEDICATED LEARNING MANAGEMENT SYSTEM

RESPONSIBILITIES:
- Course content management.
- Learning materials.
- Lessons and modules.
- Assessments.
- Course enrollment.
- Learning progress.
- Certificates.

TABLES:
- courses
- course_modules
- lessons
- learning_materials
- assessments
- assessment_questions
- assessment_attempts
- course_enrollments
- learning_progress
- certificates

DB_NAME:
hrms_lms

IMPORTANT NOTES:
- Initially, training belongs to talent-service.
- Create a separate LMS only when learning functionality becomes substantial.

----------------------------------


================================================
GLOBAL CROSS-SERVICE RULES
================================================

1. Each service owns its database.
2. Never directly access another service's database.
3. Never create cross-service database foreign keys.
4. Use UUIDs as references between services.
5. Use REST APIs for synchronous communication.
6. Use Kafka or RabbitMQ for asynchronous communication when needed.
7. Consider the transactional outbox pattern for reliable event publishing.
8. Include tenant_id on tenant-owned records.
9. Validate that referenced entities belong to the same tenant.
10. Validate JWTs in every protected service.
11. Enforce permissions in backend APIs.
12. Audit sensitive actions.
13. Keep database migrations inside the service that owns the database.
14. Do not create a microservice just because a module has its own tables.
15. Start with the 10 core services and add optional services only when needed.

================================================
CORE DATABASE LIST
================================================

1. hrms_identity
2. hrms_employee
3. hrms_scheduling
4. hrms_attendance
5. hrms_leave
6. hrms_payroll
7. hrms_recruitment
8. hrms_asset
9. hrms_talent
10. hrms_platform

OPTIONAL DATABASES:

11. hrms_expenses
12. hrms_travel
13. hrms_engagement
14. hrms_analytics
15. hrms_integrations
16. hrms_procurement
17. hrms_lms

