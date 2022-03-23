const CLASS_LIST_MODAL = {
    MODAL_REVIEW: 'review-modal',
    MODAL_ACTIVE_REVIEW: 'review-modal-active',
    TRIGGER_OPEN_REVIEW: 'js-modal-open',
    TRIGGER_CLOSE: 'js-modal-close'
};


document.addEventListener('click', (event) => {
    //open
    if (event.target.closest(`.${CLASS_LIST_MODAL.TRIGGER_OPEN_REVIEW}`)) {
        console.log('open');
        event.preventDefault();

        const target = event.target.closest(`.${CLASS_LIST_MODAL.TRIGGER_OPEN_REVIEW}`);
        const modalId = target.getAttribute('value');
        const modal = document.getElementById(modalId);
        document.body.style.overflow = 'hidden';

        modal.classList.add(CLASS_LIST_MODAL.MODAL_ACTIVE_REVIEW);

    }


    //close
    if (event.target.closest(`.${CLASS_LIST_MODAL.TRIGGER_CLOSE}`) ||
        event.target.classList.contains(CLASS_LIST_MODAL.MODAL_ACTIVE_REVIEW)
    ) {
        console.log('close');
        event.preventDefault();
        const modal = event.target.closest(`.${CLASS_LIST_MODAL.MODAL_REVIEW}`);
        document.body.style.overflow = 'visible';
        modal.classList.remove(CLASS_LIST_MODAL.MODAL_ACTIVE_REVIEW);
    }
});