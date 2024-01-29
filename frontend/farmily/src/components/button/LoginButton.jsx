import axios from 'axios';

export default function LoginButton(site) {
  function clickIcon() {
    const BASE_URL = 'http://i10e102.p.ssafy.io:8080';
    // 소셜로그인 연결하기
    // i10E102.p.ssafy.io:8080/oauth2/authorization/${site.url} 요청 보내기
    // localhost:8080/oauth2/login/code/${site.url}
    axios
      .get(BASE_URL + `/oauth2/authorization/${site.url}`, {})
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  }
  return (
    <button onClick={clickIcon} className="h-2/3">
      <img className="h-2/3 object-cover" src={site.image} alt="logo" />
      <p>{site.name} 로그인</p>
    </button>
  );
}
