const addMeetingModal = document.getElementById("add-meeting-modal");
const addMeetingBtn = document.getElementById("add-meeting-btn");
const closeAddModal = document.getElementById("close-add-modal");

addMeetingBtn.addEventListener("click", () => {
	addMeetingModal.style.display = "block";
});

closeAddModal.addEventListener("click", () => {
	addMeetingModal.style.display = "none";
});

window.addEventListener("click", (event) => {
	if (event.target === addMeetingModal) {
		addMeetingModal.style.display = "none";
	}
});
