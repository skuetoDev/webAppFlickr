export const environment = {
  production: false,
  // Usar window.location.hostname para detectar automáticamente la IP
  // o dejar localhost si estás en desarrollo local
  apiUrl: `http://${typeof window !== 'undefined' ? window.location.hostname : 'localhost'}:8080/api/images`
};
