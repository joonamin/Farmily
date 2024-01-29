export default function LoginButton(site) {
  return (
    <button className="h-2/3">
      <a
        href={`http://i10e102.p.ssafy.io:8080/oauth2/authorization/${site.url}`}
      >
        <img className="h-2/3 object-cover" src={site.image} alt="logo" />
        <p>{site.name} 로그인</p>
      </a>
    </button>
  );
}
