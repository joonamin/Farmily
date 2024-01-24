export default function LoginButton(site) {
  function clickIcon() {
    // 소셜로그인 연결하기 
    // i10E102.p.ssafy.io:8080/oauth2/login/code/${site.url} 요청 보내기
    // localhost:8080/oauth2/login/code/${site.url}
    
  }
  return(
    <button onClick={clickIcon} className="h-2/3">
      <img className="h-2/3 object-cover" src={site.image} alt="logo" />
      <p>{site.name} 로그인</p>
    </button>
  )
}
