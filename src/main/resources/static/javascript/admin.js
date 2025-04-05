const editUserModal = document.getElementById("edit-user-modal");
const editUserBtns = document.querySelectorAll("#edit-user-btn");
const closeEditUserModal = document.getElementById("close-edit-user-modal");

editUserBtns.forEach((button) => {
	button.addEventListener("click", () => {
		editUserModal.style.display = "block";
	});
});

closeEditUserModal.addEventListener("click", () => {
	editUserModal.style.display = "none";
});

window.addEventListener("click", (event) => {
	if (event.target === editUserModal) {
		editUserModal.style.display = "none";
	}
});

const editMeetingModal = document.getElementById("edit-meeting-modal");
const editMeetingBtns = document.querySelectorAll(".small-btn");
const closeEditMeetingModal = document.getElementById(
	"close-edit-meeting-modal"
);

editMeetingBtns.forEach((button) => {
	button.addEventListener("click", () => {
		editMeetingModal.style.display = "block";
	});
});

closeEditMeetingModal.addEventListener("click", () => {
	editMeetingModal.style.display = "none";
});

window.addEventListener("click", (event) => {
	if (event.target === editMeetingModal) {
		editMeetingModal.style.display = "none";
	}
});

document.querySelectorAll(".btn").forEach((button) => {
	button.addEventListener("click", (event) => {
		console.log("Saving changes...");
	});
});

const deleteUserBtns = document.querySelectorAll("#delete-user-btn");
deleteUserBtns.forEach((button) => {
	button.addEventListener("click", () => {
		if (confirm("Are you sure you want to delete this user?")) {
			console.log("User deleted");
		}
	});
});

const deleteMeetingBtns = document.querySelectorAll(".small-btn");
deleteMeetingBtns.forEach((button) => {
	button.addEventListener("click", () => {
		if (confirm("Are you sure you want to delete this meeting?")) {
			console.log("Meeting deleted");
		}
	});
});

const manageUsersBtn = document.getElementById("view-users-btn");
const manageMeetingsBtn = document.getElementById("view-meetings-btn");
const settingsBtn = document.getElementById("settings-btn");

const userManagementSection = document.getElementById("user-management");
const meetingManagementSection = document.getElementById("meeting-management");
const settingsSection = document.getElementById("settings-section");

userManagementSection.style.display = "none";
meetingManagementSection.style.display = "none";
settingsSection.style.display = "none";

manageUsersBtn.addEventListener("click", () => {
	userManagementSection.style.display = "block";
	meetingManagementSection.style.display = "none";
	settingsSection.style.display = "none";
});

manageMeetingsBtn.addEventListener("click", () => {
	userManagementSection.style.display = "none";
	meetingManagementSection.style.display = "block";
	settingsSection.style.display = "none";
});

settingsBtn.addEventListener("click", () => {
	userManagementSection.style.display = "none";
	meetingManagementSection.style.display = "none";
	settingsSection.style.display = "block";
});
