document.addEventListener("DOMContentLoaded", function () {
	const overviewSection = document.getElementById("overview-section");
	const manageProfileSection = document.getElementById(
		"manage-profile-section"
	);
	const changePasswordSection = document.getElementById(
		"change-password-section"
	);
	const meetingsSection = document.getElementById("meetings-section");

	const viewOverviewBtn = document.getElementById("view-overview-btn");
	const viewProfileBtn = document.getElementById("view-profile-btn");
	const changePasswordBtn = document.getElementById("change-password-btn");
	const meetingsBtn = document.getElementById("meetings-btn");

	// Set initial states
	overviewSection.style.display = "none";
	manageProfileSection.style.display = "none";
	changePasswordSection.style.display = "none";
	meetingsSection.style.display = "none";

	// Add event listeners to buttons
	viewOverviewBtn.addEventListener("click", () => {
		overviewSection.style.display = "block";
		manageProfileSection.style.display = "none";
		changePasswordSection.style.display = "none";
		meetingsSection.style.display = "none";
	});

	viewProfileBtn.addEventListener("click", () => {
		overviewSection.style.display = "none";
		manageProfileSection.style.display = "block";
		changePasswordSection.style.display = "none";
		meetingsSection.style.display = "none";
	});

	changePasswordBtn.addEventListener("click", () => {
		overviewSection.style.display = "none";
		manageProfileSection.style.display = "none";
		changePasswordSection.style.display = "block";
		meetingsSection.style.display = "none";
	});

	meetingsBtn.addEventListener("click", () => {
		overviewSection.style.display = "none";
		manageProfileSection.style.display = "none";
		changePasswordSection.style.display = "none";
		meetingsSection.style.display = "block";
	});
});
function validateProfileForm() {
	const username = document.getElementById("profile-username").value;
	const email = document.getElementById("profile-email").value;

	if (!username || !email) {
		alert("Name and Email cannot be empty.");
		return false;
	}

	return true;
}

// Validate password change form
function validatePasswordForm() {
	const currentPassword = document.getElementById("current-password").value;
	const newPassword = document.getElementById("new-password").value;

	// Check if all fields are filled
	if (!currentPassword || !newPassword) {
		alert("All password fields are required.");
		return false;
	}

	// Check if new password is at least 6 characters long
	if (newPassword.length < 6) {
		alert("New password must be at least 6 characters long.");
		return false;
	}

	// If both validations pass, submit the form
	return true;
}
const urlParams = new URLSearchParams(window.location.search);
if (urlParams.has("success")) {
	alert("Password changed successfully!");
} else if (urlParams.has("error")) {
	alert("Current password is incorrect!");
}
