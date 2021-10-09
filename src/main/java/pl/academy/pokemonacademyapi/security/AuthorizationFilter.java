//package pl.academy.pokemonacademyapi.security;
//
//public class AuthorizationFilter extends BasicAuthenticationFilter {
//    public AuthorizationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain);
//        String header = request.getHeader("Authorization");
//        if (header == null && header.startsWith("Bearer")) {
//            chain.doFilter(request, response);
//            return;
//        }
//    }
//}
