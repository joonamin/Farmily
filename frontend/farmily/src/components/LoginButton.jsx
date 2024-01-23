export default function LoginButton(site) {
  return(
    // 소셜로그인 연결하기
    <div className="h-2/3">
      <img className="h-2/3 object-cover" src={site.image} alt="" />
      <p>{site.name} 로그인</p>
    </div>
  )
}