import { createRouter, createWebHistory } from 'vue-router';
import { useStore } from 'vuex';

// Import components
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import LogoutView from '../views/LogoutView.vue';
import RegisterView from '../views/RegisterView.vue';
import EventView from '../views/EventView.vue';
import PlaylistView from '../views/PlaylistView.vue';
import LandingPageView from '../views/LandingPageView.vue';

const routes = [
  {
    path: '/',
    name: 'landingPage',
    component: LandingPageView,
    meta: { requiresAuth: false, hideNavbar: true }, // Navbar hidden
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    meta: { requiresAuth: true, hideNavbar: false }, // Navbar visible
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { requiresAuth: false, hideNavbar: true }, // Navbar hidden
  },
  {
    path: '/logout',
    name: 'logout',
    component: LogoutView,
    meta: { requiresAuth: false, hideNavbar: true }, // Navbar hidden
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
    meta: { requiresAuth: false, hideNavbar: true }, // Navbar hidden
  },
  {
    path: '/events/:eventId',
    name: 'EventView',
    component: EventView,
    props: true,
    meta: { requiresAuth: true, hideNavbar: false }, // Navbar visible
  },
  {
    path: '/playlists/:playlistId',
    name: 'PlaylistView',
    component: PlaylistView,
    props: true,
    meta: { requiresAuth: false, hideNavbar: false }, // Navbar visible
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: LandingPageView, // Redirect to Landing Page for undefined routes
    meta: { requiresAuth: false, hideNavbar: true },
  },
];

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Navigation guard to check authentication
router.beforeEach((to) => {
  const store = useStore();

  // Determine if the route requires authentication
  const requiresAuth = to.matched.some((x) => x.meta.requiresAuth);

  // If authentication is required and the user is not logged in, redirect to login
  if (requiresAuth && store.state.token === '') {
    return { name: 'login' };
  }
});

export default router;
