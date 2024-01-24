import LoginButton from '../components/LoginButton.jsx'
import meta from '../assets/images/meta.png'
import kakao from '../assets/images/kakao.png'
import google from '../assets/images/google.png'
import farmily from '../assets/images/farmily.png'

const sites = [
  { name: '카카오', url: 'kakao', image: kakao },
  { name: '구글', url: 'google', image: google },
  { name: '메타 ', url: 'facebook', image: meta },
]

export default function LoginPage() {
  return(
    <div className="h-screen text-center align-middle w-full py-24 px-60 ">
      <div className="border-8 border-black bg-white h-full rounded-xl p-10">
      <h1 className="text-2xl mb-5"> 로그인</h1>
      <div className="h-1/3 flex justify-center">
        <img src={farmily} alt="" />

      </div>
        <div className="h-2/3 flex justify-around items-center p-10">
          {sites.map((site, index) => (
            <LoginButton key={index} name={site.name} url={site.url} image={site.image} />
          ))}
        </div>
      </div>
    </div>
  )
}