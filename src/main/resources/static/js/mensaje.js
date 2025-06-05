// Oculta el mensaje después de 3 segundos
setTimeout(function() {
    const mensaje = document.getElementById('mensaje');
    if (mensaje) {
        mensaje.style.transition = 'opacity 0.5s ease';
        mensaje.style.opacity = '0';
        setTimeout(() => mensaje.remove(), 300);
    }
}, 3000);