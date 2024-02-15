import LoginButton from '../components/button/LoginButton.jsx';
import kakao from '../assets/images/kakao.png';
import google from '../assets/images/google.png';
import farmily from '../assets/images/Farmily.png';

const sites = [
  { name: '카카오', url: 'kakao', image: kakao },
  { name: '구글', url: 'google', image: google },
];

export default function LoginPage() {
  return (
    <div className="h-screen text-center align-middle w-full py-24 px-60 z-20">
      <div className="border-8 border-black bg-white h-full rounded-xl p-10">
        <h1 className="text-2xl mb-5"> 로그인</h1>
        <div className="h-1/2 flex justify-center">
          <img src={farmily} alt="" />
        </div>
        <div className="h-1/2 flex justify-around items-center px-10">
          {sites.map((site, index) => (
            <LoginButton key={index} name={site.name} url={site.url} image={site.image} />
          ))}
        </div>
      </div>
    </div>
  );
}
