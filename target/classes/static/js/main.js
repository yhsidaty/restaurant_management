// ============================================
// GESTION RESTAURANT — MAIN JS
// ============================================

// Modal open/close
function openModal(id) {
    document.getElementById(id).classList.add('open');
}
function closeModal(id) {
    document.getElementById(id).classList.remove('open');
}

// Category accordion toggle
function toggleCategory(id) {
    const body = document.getElementById('cat-body-' + id);
    if (body) body.classList.toggle('open');
}

// Language toggle (stored in localStorage)
function setLanguage(lang) {
    localStorage.setItem('lang', lang);
    document.documentElement.lang = lang;
    // Simple reload to let server handle if needed, or just set data-lang
    document.querySelectorAll('[data-en],[data-fr]').forEach(el => {
        const text = lang === 'en' ? el.dataset.en : el.dataset.fr;
        if (text) el.textContent = text;
    });
    // Update active button
    document.querySelectorAll('.lang-btn').forEach(b => b.classList.remove('active'));
    const btn = document.getElementById('lang-' + lang);
    if (btn) btn.classList.add('active');
}

// On load — apply stored language
document.addEventListener('DOMContentLoaded', () => {
    const lang = localStorage.getItem('lang') || 'fr';
    setLanguage(lang);

    // Auto-dismiss alerts
    document.querySelectorAll('.alert').forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.4s';
            setTimeout(() => alert.remove(), 400);
        }, 4000);
    });

    // Close modal on overlay click
    document.querySelectorAll('.modal-overlay').forEach(overlay => {
        overlay.addEventListener('click', e => {
            if (e.target === overlay) overlay.classList.remove('open');
        });
    });
});
