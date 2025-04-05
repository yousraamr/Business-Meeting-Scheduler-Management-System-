const editProfileModal = document.getElementById("edit-profile-modal");
const historyModal = document.getElementById("history-modal");
const upcomingMeetingsModal = document.getElementById(
	"upcoming-meetings-modal"
);

const editProfileBtn = document.getElementById("edit-profile-btn");
const historyBtn = document.getElementById("history-btn");
const upcomingMeetingsBtn = document.getElementById("upcoming-meetings-btn");

const closeEditModal = document.getElementById("close-edit-modal");
const closeHistoryModal = document.getElementById("close-history-modal");
const closeUpcomingModal = document.getElementById("close-upcoming-modal");

editProfileBtn.addEventListener("click", () => {
	editProfileModal.style.display = "block";
});

historyBtn.addEventListener("click", () => {
	historyModal.style.display = "block";
});

upcomingMeetingsBtn.addEventListener("click", () => {
	upcomingMeetingsModal.style.display = "block";
});

closeEditModal.addEventListener("click", () => {
	editProfileModal.style.display = "none";
});

closeHistoryModal.addEventListener("click", () => {
	historyModal.style.display = "none";
});

closeUpcomingModal.addEventListener("click", () => {
	upcomingMeetingsModal.style.display = "none";
});

window.addEventListener("click", (event) => {
	if (event.target === editProfileModal) {
		editProfileModal.style.display = "none";
	}
	if (event.target === historyModal) {
		historyModal.style.display = "none";
	}
	if (event.target === upcomingMeetingsModal) {
		upcomingMeetingsModal.style.display = "none";
	}
});
